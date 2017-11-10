package com.ruitukeji.novate.entity;

import com.common.cklibrary.entity.BaseResult;

import java.util.List;

/**
 * 应用配置参数
 * Created by Administrator on 2017/5/31.
 */

public class AppConfigBean extends BaseResult<AppConfigBean.ResultBean> {

    public static class ResultBean {
        /**
         * lastApkUrl : https://space.dingtalk.com/c/ggHaACQzMGFjMGIzOC1hZWE3LTRiYTEtOWI0OS1hNDRiOTM4Y2MyZTACzhmbUfA
         * lastApkVersion : V0.0.1
         * lastApkVersionNum : 1
         * defaultAvatar : http://opmnz562z.bkt.clouddn.com/b22e33502ca2e6e1d93a996e062e8f2d.png
         * payWays : ["货到验收合格，票到120天付款","货到验收合格，票到90天付款","货到验收合格，票到30天付款","预付30%，发货前付70%","预付30%，货到现场30%，安装调试合格30%，一年质保金10%"]
         * xx : 其他参数
         */

        private String lastApkUrl;
        private String lastApkVersion;
        private String lastApkVersionNum;
        private String defaultAvatar;
        private String xx;
        private List<String> payWays;

        public String getLastApkUrl() {
            return lastApkUrl;
        }

        public void setLastApkUrl(String lastApkUrl) {
            this.lastApkUrl = lastApkUrl;
        }

        public String getLastApkVersion() {
            return lastApkVersion;
        }

        public void setLastApkVersion(String lastApkVersion) {
            this.lastApkVersion = lastApkVersion;
        }

        public String getLastApkVersionNum() {
            return lastApkVersionNum;
        }

        public void setLastApkVersionNum(String lastApkVersionNum) {
            this.lastApkVersionNum = lastApkVersionNum;
        }

        public String getDefaultAvatar() {
            return defaultAvatar;
        }

        public void setDefaultAvatar(String defaultAvatar) {
            this.defaultAvatar = defaultAvatar;
        }

        public String getXx() {
            return xx;
        }

        public void setXx(String xx) {
            this.xx = xx;
        }

        public List<String> getPayWays() {
            return payWays;
        }

        public void setPayWays(List<String> payWays) {
            this.payWays = payWays;
        }
    }
}
