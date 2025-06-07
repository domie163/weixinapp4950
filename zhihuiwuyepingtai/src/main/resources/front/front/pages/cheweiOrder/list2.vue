<template>
    <view>
        <scroll-view  scroll-x class="bg-white nav">
            <view class='flex text-center'>
                <view class="cu-item flex-sub text-df " @click="navselect(0)"
                      :class="[currentIndex == 0 ? 'text-red cur' : '']">
                    全部
                </view>
                <view class="cu-item flex-sub text-df " @click="navselect(nav.codeIndex)" v-for="(nav,index) in navlist" :key="index"
                      :class="[currentIndex == nav.codeIndex ? 'text-red cur' : '']">
                    {{nav.indexName}}
                </view>
            </view>
        </scroll-view>
        <mescroll-uni :top="88" @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback">
            <view v-for="(item,index) in list" :key="index">
                <view class="bg-white margin-sm padding-sm solids-bottom radius text-content">
                    <view class="flex justify-between">
                        <view class="text-bold" style="flex-shrink: 0;">
                            <text class="text-green">{{ item.cheweiOrderTypes == 101?item.cheweiOrderValue:'' }}</text>
                            <text class="text-red">{{ item.cheweiOrderTypes == 102?item.cheweiOrderValue:'' }}</text>
                            <text class="text-grey">{{ item.cheweiOrderTypes == 103?item.cheweiOrderValue:'' }}</text>
                            <text class="text-blue">{{ item.cheweiOrderTypes == 104?item.cheweiOrderValue:'' }}</text>
                            <text class="text-brown">{{ item.cheweiOrderTypes == 105?item.cheweiOrderValue:'' }}</text>
                        </view>
                        <view><text class="icon-chewei padding-right-sm"></text><text class="text-bold solids-bottom">{{ item.cheweiOrderUuidNumber }}</text></view>
                    </view>
                    <view class="bg-white margin-xs padding-xs  radius text-content">
                        <view class="flex padding-top-sm solids-top">
                            <image :src="baseUrl+item.cheweiPhoto" style="flex-shrink: 0;" mode="aspectFill" class=" cover radius solids"></image>
                            <view class="text-content padding-left" style="flex-grow: 1;">
                                <view>{{ item.cheweiName }}</view>
                                <view>{{ item.buyZuoweiTime }}</view>
                                <view >{{ item.buyNumber }} x ￥{{ item.cheweiNewMoney }}</view>
                            </view>
                        </view>
                        <view class="flex justify-end">合计{{ item.cheweiOrderTruePrice }}元</view>
                    </view>
                    <view class="flex justify-end"></view>
                    <view class="flex justify-end" style="font-size: 24upx;">
                        <view>
                            <view @tap="receiving(item.id)" class="round cu-btn lines-grey mid  margin-right-sm">取车</view>
                        </view>
                    </view>
                </view>
            </view>




        </mescroll-uni>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                navlist: [
                ],
                currentIndex: 0,
                pages: 1,
                list: [],
                mescroll: null, //mescroll实例对象
                downOption: {
                    auto: false //是否在初始化后,自动执行下拉回调callback; 默认true
                },
                upOption: {
                    page: {
                        // num: 0, // 当前页码,默认0,回调之前会加1,即callback(page)会从1开始
                        // size: 5 // 每页数据的数量
                    },
                    noMoreSize: 3, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
                    textNoMore: '~ 没有更多了 ~',
                },
                hasNext: true,
                cheweiLiuyanText: '',
                user: '',
                //评价
                cheweiId:null,
                cheweiCommentbackText: null,
				cheweiCommentbackPingfenNumber:0,

            }
        },
        computed: {
            baseUrl() {
                return this.$base.url;
            },
        },
        async onShow() {
            let table = uni.getStorageSync("nowTable");
            let res = await this.$api.session(table)
            this.user = res.data
            this.hasNext = true

            let params = {
                page: 1,
                limit: 100,
                dicCode: 'chewei_order_types',
            }
            let cheweiOrderTypesRes = await this.$api.page(`dictionary`, params);

            this.navlist = cheweiOrderTypesRes.data.list

            // 重新加载数据
            if (this.mescroll) this.mescroll.resetUpScroll()
        },
        methods: {
            navselect(nav) {
                this.currentIndex = nav
                this.cheweiOrderTypes = nav
                this.hasNext = true
                // 重新加载数据
                if (this.mescroll) this.mescroll.resetUpScroll()
            },
            // mescroll组件初始化的回调,可获取到mescroll对象
            mescrollInit(mescroll) {
                this.mescroll = mescroll;
            },
            /*下拉刷新的回调 */
            downCallback(mescroll) {
                this.hasNext = true
                // 重置分页参数页数为1
                mescroll.resetUpScroll()
            },
            /*上拉加载的回调: mescroll携带page的参数, 其中num:当前页 从1开始, size:每页数据条数,默认10 */
            async upCallback(mescroll) {
                let params = {
                    page: mescroll.num,
                    limit: mescroll.size,
                }
                if (this.cheweiOrderTypes) {
                    params['cheweiOrderTypes'] = this.cheweiOrderTypes
                }
                let res = await this.$api.page('cheweiOrder', params);
                // 如果是第一页数据置空
                if (mescroll.num == 1) this.list = [];
                this.list = this.list.concat(res.data.list);
                if (res.data.list.length == 0) this.hasNext = false;
                mescroll.endSuccess(mescroll.size, this.hasNext);
            },
            //取车
            async receiving(id){
                let _this = this;
                uni.showModal({
                    title: '提示',
                    content: '是否确认要取车？',
                    success: async function(res) {
                        if(res.confirm){
                            let order = [
                                {
                                    key: 'id',
                                    val: id
                                }
                            ]
                            var returnVal = await _this.$api.requestCondition("cheweiOrder","receiving",order);
                            _this.$utils.msg('操作成功');
                            if (_this.mescroll) _this.mescroll.resetUpScroll()
                        }
                    }
                });
            },
        }
    }
</script>

<style>
    .cover {
        width: 150upx;
        height: 150upx;
    }
</style>
