
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 便民服务
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/bianmin")
public class BianminController {
    private static final Logger logger = LoggerFactory.getLogger(BianminController.class);

    private static final String TABLE_NAME = "bianmin";

    @Autowired
    private BianminService bianminService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private BaoxiuService baoxiuService;//报修
    @Autowired
    private ChatService chatService;//投诉管理
    @Autowired
    private CheweiService cheweiService;//车位
    @Autowired
    private CheweiOrderService cheweiOrderService;//车位订单
    @Autowired
    private DictionaryService dictionaryService;//字典
    @Autowired
    private ExampaperService exampaperService;//试卷
    @Autowired
    private ExampapertopicService exampapertopicService;//试卷选题
    @Autowired
    private ExamquestionService examquestionService;//试题表
    @Autowired
    private ExamrecordService examrecordService;//考试记录表
    @Autowired
    private ExamredetailsService examredetailsService;//答题详情表
    @Autowired
    private ExamrewrongquestionService examrewrongquestionService;//错题表
    @Autowired
    private FangwuService fangwuService;//房屋
    @Autowired
    private GonggaoService gonggaoService;//公告
    @Autowired
    private JiaofeiService jiaofeiService;//缴费
    @Autowired
    private WeixuiService weixuiService;//维修指派
    @Autowired
    private YonghuService yonghuService;//用户
    @Autowired
    private YuangongService yuangongService;//员工
    @Autowired
    private UsersService usersService;//管理员


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("员工".equals(role))
            params.put("yuangongId",request.getSession().getAttribute("userId"));
        params.put("bianminDeleteStart",1);params.put("bianminDeleteEnd",1);
        CommonUtil.checkMap(params);
        PageUtils page = bianminService.queryPage(params);

        //字典表数据转换
        List<BianminView> list =(List<BianminView>)page.getList();
        for(BianminView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        BianminEntity bianmin = bianminService.selectById(id);
        if(bianmin !=null){
            //entity转view
            BianminView view = new BianminView();
            BeanUtils.copyProperties( bianmin , view );//把实体数据重构到view中
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody BianminEntity bianmin, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,bianmin:{}",this.getClass().getName(),bianmin.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<BianminEntity> queryWrapper = new EntityWrapper<BianminEntity>()
            .eq("bianmin_name", bianmin.getBianminName())
            .eq("bianmin_address", bianmin.getBianminAddress())
            .eq("bianmin_types", bianmin.getBianminTypes())
            .eq("bianmin_delete", 1)
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        BianminEntity bianminEntity = bianminService.selectOne(queryWrapper);
        if(bianminEntity==null){
            bianmin.setBianminDelete(1);
            bianmin.setInsertTime(new Date());
            bianmin.setCreateTime(new Date());
            bianminService.insert(bianmin);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody BianminEntity bianmin, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,bianmin:{}",this.getClass().getName(),bianmin.toString());
        BianminEntity oldBianminEntity = bianminService.selectById(bianmin.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        if("".equals(bianmin.getBianminPhoto()) || "null".equals(bianmin.getBianminPhoto())){
                bianmin.setBianminPhoto(null);
        }
        if("".equals(bianmin.getBianminContent()) || "null".equals(bianmin.getBianminContent())){
                bianmin.setBianminContent(null);
        }

            bianminService.updateById(bianmin);//根据id更新
            return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<BianminEntity> oldBianminList =bianminService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        ArrayList<BianminEntity> list = new ArrayList<>();
        for(Integer id:ids){
            BianminEntity bianminEntity = new BianminEntity();
            bianminEntity.setId(id);
            bianminEntity.setBianminDelete(2);
            list.add(bianminEntity);
        }
        if(list != null && list.size() >0){
            bianminService.updateBatchById(list);
        }

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //.eq("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        try {
            List<BianminEntity> bianminList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            BianminEntity bianminEntity = new BianminEntity();
//                            bianminEntity.setBianminName(data.get(0));                    //便民服务名称 要改的
//                            bianminEntity.setBianminUuidNumber(data.get(0));                    //便民服务编号 要改的
//                            bianminEntity.setBianminPhoto("");//详情和图片
//                            bianminEntity.setBianminAddress(data.get(0));                    //便民服务地点 要改的
//                            bianminEntity.setBianminTypes(Integer.valueOf(data.get(0)));   //便民服务类型 要改的
//                            bianminEntity.setBianminContent("");//详情和图片
//                            bianminEntity.setBianminDelete(1);//逻辑删除字段
//                            bianminEntity.setInsertTime(date);//时间
//                            bianminEntity.setCreateTime(date);//时间
                            bianminList.add(bianminEntity);


                            //把要查询是否重复的字段放入map中
                                //便民服务编号
                                if(seachFields.containsKey("bianminUuidNumber")){
                                    List<String> bianminUuidNumber = seachFields.get("bianminUuidNumber");
                                    bianminUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> bianminUuidNumber = new ArrayList<>();
                                    bianminUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("bianminUuidNumber",bianminUuidNumber);
                                }
                        }

                        //查询是否重复
                         //便民服务编号
                        List<BianminEntity> bianminEntities_bianminUuidNumber = bianminService.selectList(new EntityWrapper<BianminEntity>().in("bianmin_uuid_number", seachFields.get("bianminUuidNumber")).eq("bianmin_delete", 1));
                        if(bianminEntities_bianminUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(BianminEntity s:bianminEntities_bianminUuidNumber){
                                repeatFields.add(s.getBianminUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [便民服务编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        bianminService.insertBatch(bianminList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }




    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = bianminService.queryPage(params);

        //字典表数据转换
        List<BianminView> list =(List<BianminView>)page.getList();
        for(BianminView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        BianminEntity bianmin = bianminService.selectById(id);
            if(bianmin !=null){


                //entity转view
                BianminView view = new BianminView();
                BeanUtils.copyProperties( bianmin , view );//把实体数据重构到view中

                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody BianminEntity bianmin, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,bianmin:{}",this.getClass().getName(),bianmin.toString());
        Wrapper<BianminEntity> queryWrapper = new EntityWrapper<BianminEntity>()
            .eq("bianmin_name", bianmin.getBianminName())
            .eq("bianmin_uuid_number", bianmin.getBianminUuidNumber())
            .eq("bianmin_address", bianmin.getBianminAddress())
            .eq("bianmin_types", bianmin.getBianminTypes())
            .eq("bianmin_delete", bianmin.getBianminDelete())
//            .notIn("bianmin_types", new Integer[]{102})
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        BianminEntity bianminEntity = bianminService.selectOne(queryWrapper);
        if(bianminEntity==null){
            bianmin.setBianminDelete(1);
            bianmin.setInsertTime(new Date());
            bianmin.setCreateTime(new Date());
        bianminService.insert(bianmin);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}

