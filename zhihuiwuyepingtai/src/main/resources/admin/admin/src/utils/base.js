const base = {
    get() {
        return {
            url : "http://localhost:8080/zhihuiwuyepingtai/",
            name: "zhihuiwuyepingtai",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/zhihuiwuyepingtai/front/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "智慧物业平台"
        } 
    }
}
export default base
