package indi.wzl.test.bean;

import indi.wzl.annotation.ExcelCell;
import indi.wzl.annotation.HeadName;

import java.io.Serializable;

/**
 * 报名名单
 *
 * @author zonglin_wu
 * @create 2017-04-28 16:06
 **/
public class SignUp implements Serializable{
    //企业id
    private int corpId;
    //创建人
    private int creator;
    //修改人
    private int whoModified;
    //创建时间
    private String timeCreated;
    //修改时间
    private String timeModified;
    //uuid
    private String uuid;
    //姓名
    @ExcelCell(0)
    @HeadName("姓名")
    private String name;
    //公司名
    @ExcelCell(1)
    @HeadName("公司名")
    private String company;
    //职位
    @ExcelCell(2)
    @HeadName("职位")
    private String position;
    //活动标识，目前弃用
    private String activetype;
    //手机号
    @ExcelCell(3)
    @HeadName("手机号")
    private String mobile;
    //报名时间
    private String upTime;
    //邮箱
    @ExcelCell(4)
    @HeadName("邮箱")
    private String mailbox;
    //活动id
    private String activityId;
    //报名所在的ip
    private String upIp;
    //是否签到
    private String isSignIn;

    public int getCorpId() {
        return corpId;
    }

    public void setCorpId(int corpId) {
        this.corpId = corpId;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getWhoModified() {
        return whoModified;
    }

    public void setWhoModified(int whoModified) {
        this.whoModified = whoModified;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(String timeModified) {
        this.timeModified = timeModified;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getActivetype() {
        return activetype;
    }

    public void setActivetype(String activetype) {
        this.activetype = activetype;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUpIp() {
        return upIp;
    }

    public void setUpIp(String upIp) {
        this.upIp = upIp;
    }

    public String getIsSignIn() {
        return isSignIn;
    }

    public void setIsSignIn(String isSignIn) {
        this.isSignIn = isSignIn;
    }

}
