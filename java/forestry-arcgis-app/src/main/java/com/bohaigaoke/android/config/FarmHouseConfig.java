package com.bohaigaoke.android.config;

import android.os.Environment;

import com.bohaigaoke.forestry.R;


public class FarmHouseConfig {
    private String systemConfigPath = Environment.getExternalStorageDirectory().getPath() + "/ldt/";

    /**
     */
    private int msgTaskRequestSeparate = 120;


    private int uploadImageLength = 800;


    /**
     */
    private String dutyGridLayerIndex = "-1";

    /**
     * ����ͼ�������ֵ
     */
    private String partLayerIndex = "3";

    /**
     * ����ͼ�������ֵ
     */
    private String gridLayerIndex = "0";

    /**
     * ÿҳ��ʾ����
     */
    private int pageSize = 3;

    private int dbVersion = 1;


    private String urlLogin = "login";

    /**
     * ������ѯ
     */
    private String urlQueryCase = "util/mb_queryCGTCase";
    private String urlQueryCaseSearch = "util/mb_queryCaseSearch";
    private String urlQueryCaseAudio = "uploadFile/";
    /**
     * ��Ա��ѯ
     */
    private String urlQueryPerson = "util/mb_queryPerson";
    private String urlQueryPersonSearch = "util/mb_querySearchPerson";
    private String urlQueryPersonPoi = "util/mb_queryRealData";

    /**
     * ������ʵ���˲�����
     */
    private String urlHints = "util/mb_getHints";


    /**
     * ��������
     */
    private String urlChangePassword = "util/mb_changePassword";

    /**
     * �绰����
     */
    private String phoneNum = " ";


    /**
     * ���ݾ�γ�Ȼ�ȡ���ء��ֵ�������������
     */
    private String urlRangeByPoint = "util/ajax_getRangeByPoint";

    /**
     * �¼����������Դ����¼����Դ������ϱ���ַ
     */
    private String urlCaseUpload = "util/mb_mbEventUpload";


    /**
     * ��Ϣ��ѯ
     */
    private String urlQueryMessage = "util/mb_queryMessage";

    /**
     * ��Ϣ����
     */
    private String urlUpdateMessage = "util/mb_updateMessage";

    /**
     * ���������ϵ��
     */
    private String urlAllContacts = "util/mb_getAllContacts";

    /**
     * �����ϵ��
     */
    private String urlAddContact = "util/mb_addContact";

    /**
     * ������ϵ��
     */
    private String urlUpdateContact = "util/mb_updateContact";


    /**
     * ɾ����ϵ��
     */
    private String urlDeleteContact = "util/mb_deleteContact";

    /**
     * �켣�ϴ�
     */
    private String urlSaveTrace = "util/mb_saveTrace";


    /**
     * ����С���б�
     */
    private String urlGetCaseTypeList = "housetype";

    private String urlGetRelationList = "relationship";

    public String getUrlGetRelationList() {
        return urlGetRelationList;
    }

    public void setUrlGetRelationList(String urlGetRelationList) {
        this.urlGetRelationList = urlGetRelationList;
    }

    /**
     * ������ʵ
     */
    private String urlCaseVerify = "util/mb_mbCaseVerify";

    /**
     * �����˲�
     */
    private String urlCaseCheck = "util/mb_mbCaseCheck";

    /**
     * ���� ��������
     */
    private String urlGetAllUnits = "util/mb_getAllUnits";

    /**
     * �ύ�²���
     */
    private String urlSaveNewPart = "util/mb_saveNewPart";


    /**
     * map��̨����URL
     */

    private String mapRequestUrl = "http://221.212.125.113:6080/arcgis/rest/services/NH/";

    /**
     * ��ͼ����URL
     */
    private String mapServiceUrl = "NEHE_DLG_20141011_0817/MapServer";

    /**
     * ��ͼ��ѯ����URL
     */
    private String mapQueryServiceUrl = "NH_CGTEST_20150610/MapServer";


    /**
     * apk���ص�ַ
     */
    private String updateApkUrl = "http://221.212.125.102:90/cg/version/ldt.apk";

    /**
     * apk�ļ�����ַ
     */
    private String checkApkUrl = "http://221.212.125.102:90/cg/version/version.txt";


    /**
     * �켣�ϴ��ļ��ʱ��
     */
    private int gpsReportTimer = 30;

    /**
     * �켣�ϴ��ļ������
     */
    private int gpsReportLength = 20;


    private int nowTheme = R.style.AppTheme;

    private boolean fullScreen = false;


    /**
     * ����������Ϣ
     */
    public void saveKysldtConfig() {
        new ConfigLoader().saveConfig(this);
    }


    /**
     * @return ϵͳ������ϢĿ¼
     */
    public String getSystemConfigPath() {
        return systemConfigPath;
    }

  

    public String getUrlLogin() {
        return urlLogin;
    }


    public void setUrlLogin(String urlLogin) {
        this.urlLogin = urlLogin;
    }


    public String getUrlRangeByPoint() {
        return urlRangeByPoint;
    }


    public String getUrlHints() {
        return urlHints;
    }


    public void setUrlHints(String urlHints) {
        this.urlHints = urlHints;
    }

    public String getUrlChangePassword() {
        return urlChangePassword;
    }


    public void setUrlChangePassword(String urlChangePassword) {
        this.urlChangePassword = urlChangePassword;
    }


    public String getPhoneNum() {
        return phoneNum;
    }


    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public int getUploadImageLength() {
        return uploadImageLength;
    }


    public void setUploadImageLength(int uploadImageLength) {
        this.uploadImageLength = uploadImageLength;
    }


    public void setUrlRangeByPoint(String urlRangeByPoint) {
        this.urlRangeByPoint = urlRangeByPoint;
    }


    public String getUrlCaseUpload() {
        return urlCaseUpload;
    }


    public void setUrlCaseUpload(String urlCaseUpload) {
        this.urlCaseUpload = urlCaseUpload;
    }


    public String getUrlQueryCase() {
        return urlQueryCase;
    }

    public String getUrlQueryCaseSearch() {
        return urlQueryCaseSearch;
    }

    public String getUrlQueryCaseAudio() {
        return urlQueryCaseAudio;
    }

    public String getUrlQueryPerson() {
        return urlQueryPerson;
    }

    public String getUrlQueryPersonSearch() {
        return urlQueryPersonSearch;
    }

    public String getUrlQueryPersonPoi() {
        return urlQueryPersonPoi;
    }


    public void setUrlQueryCase(String urlQueryCase) {
        this.urlQueryCase = urlQueryCase;
    }


    public String getUrlQueryMessage() {
        return urlQueryMessage;
    }


    public void setUrlQueryMessage(String urlQueryMessage) {
        this.urlQueryMessage = urlQueryMessage;
    }


    public String getUrlUpdateMessage() {
        return urlUpdateMessage;
    }


    public void setUrlUpdateMessage(String urlUpdateMessage) {
        this.urlUpdateMessage = urlUpdateMessage;
    }


    public String getUrlAllContacts() {
        return urlAllContacts;
    }


    public void setUrlAllContacts(String urlAllContacts) {
        this.urlAllContacts = urlAllContacts;
    }


    public String getUrlAddContact() {
        return urlAddContact;
    }


    public void setUrlAddContact(String urlAddContact) {
        this.urlAddContact = urlAddContact;
    }


    public String getUrlUpdateContact() {
        return urlUpdateContact;
    }


    public void setUrlUpdateContact(String urlUpdateContact) {
        this.urlUpdateContact = urlUpdateContact;
    }


    public String getUrlDeleteContact() {
        return urlDeleteContact;
    }


    public void setUrlDeleteContact(String urlDeleteContact) {
        this.urlDeleteContact = urlDeleteContact;
    }


    public String getMapRequestUrl() {
        return mapRequestUrl;
    }


    public void setMapRequestUrl(String mapRequestUrl) {
        this.mapRequestUrl = mapRequestUrl;
    }


    /**
     * @return the pictureViewUrl ��ͼ����URL
     */
    public String getMapServiceUrl() {
        return mapServiceUrl;
    }

    /**
     * @param mapServiceUrl the pictureViewUrl to set ��ͼ����URL
     */
    public void setMapServiceUrl(String mapServiceUrl) {
        this.mapServiceUrl = mapServiceUrl;
    }

    public String getMapQueryServiceUrl() {
        return mapQueryServiceUrl;
    }


    public void setMapQueryServiceUrl(String mapQueryServiceUrl) {
        this.mapQueryServiceUrl = mapQueryServiceUrl;
    }


    public void setSystemConfigPath(String systemConfigPath) {
        this.systemConfigPath = systemConfigPath;
    }


    /**
     * apk���µ�ַ
     *
     * @return
     */
    public String getUpdateApkUrl() {
        return updateApkUrl;
    }


    public void setUpdateApkUrl(String updateApkUrl) {
        this.updateApkUrl = updateApkUrl;
    }


    public String getUrlCaseVerify() {
        return urlCaseVerify;
    }


    public void setUrlCaseVerify(String urlCaseVerify) {
        this.urlCaseVerify = urlCaseVerify;
    }

    public String getUrlCaseCheck() {
        return urlCaseCheck;
    }


    public void setUrlCaseCheck(String urlCaseCheck) {
        this.urlCaseCheck = urlCaseCheck;
    }


    public String getUrlGetAllUnits() {
        return urlGetAllUnits;
    }


    public void setUrlGetAllUnits(String urlGetAllUnits) {
        this.urlGetAllUnits = urlGetAllUnits;
    }


    public String getUrlSaveNewPart() {
        return urlSaveNewPart;
    }


    public void setUrlSaveNewPart(String urlSaveNewPart) {
        this.urlSaveNewPart = urlSaveNewPart;
    }


    /**
     * apk����ַ
     *
     * @return
     */
    public String getCheckApkUrl() {
        return checkApkUrl;
    }


    public void setCheckApkUrl(String checkApkUrl) {
        this.checkApkUrl = checkApkUrl;
    }


    public String getUrlSaveTrace() {
        return urlSaveTrace;
    }


    public void setUrlSaveTrace(String urlSaveTrace) {
        this.urlSaveTrace = urlSaveTrace;
    }


    public String getUrlGetCaseTypeList() {
        return urlGetCaseTypeList;
    }


    public void setUrlGetCaseTypeList(String urlGetCaseTypeList) {
        this.urlGetCaseTypeList = urlGetCaseTypeList;
    }


    /**
     * @return the partLayerIndex����ͼ�������ֵ
     */
    public String getPartLayerIndex() {
        return partLayerIndex;
    }

    /**
     * @param partLayerIndex the partLayerIndex to set����ͼ�������ֵ
     */
    public void setPartLayerIndex(String partLayerIndex) {
        this.partLayerIndex = partLayerIndex;
    }

    /**
     * @return the gridLayerIndex����ͼ������ֵ
     */
    public String getGridLayerIndex() {
        return gridLayerIndex;
    }

    /**
     * @param gridLayerIndex the gridLayerIndex to set����ͼ������ֵ
     */
    public void setGridLayerIndex(String gridLayerIndex) {
        this.gridLayerIndex = gridLayerIndex;
    }

    /**
     * ����GPS�ر�ʱ����
     *
     * @param gpsReportTimer
     */
    public void setGpsReportTimer(int gpsReportTimer) {
        this.gpsReportTimer = gpsReportTimer;
    }

    /**
     * @return����GPS�ر�ʱ����
     */
    public int getGpsReportTimer() {
        return gpsReportTimer;
    }

    public void setGpsReportLength(int gpsReportLength) {
        this.gpsReportLength = gpsReportLength;
    }

    /**
     * @return����GPS�ر�������
     */
    public int getGpsReportLength() {
        return gpsReportLength;
    }

    /**
     * ��ҳ��ҳ��¼��
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setNowTheme(int nowTheme) {
        if (this.nowTheme != nowTheme) {
            this.nowTheme = nowTheme;
        }
    }

    public int getNowTheme() {
        return nowTheme;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }


    /**
     * ���ݿ�汾
     *
     * @param dbVersion
     */
    public void setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
        new ConfigLoader().saveValue("DbVersion", dbVersion);
    }

    /**
     * @return���ݿ�汾
     */
    public int getDbVersion() {
        return dbVersion;
    }

    public void setMsgTaskRequestSeparate(int msgTaskRequestSeparate) {
        this.msgTaskRequestSeparate = msgTaskRequestSeparate;
    }

    /**
     * @return��ҳ������Ϣ���������ļ��
     */
    public int getMsgTaskRequestSeparate() {
        return msgTaskRequestSeparate;
    }

    public void setDutyGridLayerIndex(String dutyGridLayerIndex) {
        this.dutyGridLayerIndex = dutyGridLayerIndex;
    }

    /**
     * �������������
     *
     * @return
     */
    public String getDutyGridLayerIndex() {
        return dutyGridLayerIndex;
    }
}
