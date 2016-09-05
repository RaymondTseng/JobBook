package com.example.jobbook.cv.view;

import com.example.jobbook.bean.TextCVBean;

/**
 * Created by 椰树 on 2016/9/4.
 */
public interface TextCVView {
    /**
     * 成功
     */
    void success();
    /**
     * 网络错误
     */
    void networkError();
    /**
     * 进度出现
     */
    void showProgress();

    /**
     * 隐藏进度
     */
    void hideProgress();

    /**
     * 头像为空错误
     */
    void headBlankError();

    /**
     * 名字为空错误
     */
    void nameBlankError();

    /**
     * 最高学历为空错误
     */
    void qualificationBlankError();

    /**
     * 所在城市为空错误
     */
    void locationBlankError();

    /**
     * 残疾等级为空错误
     */
    void levelBlankError();

    /**
     * 残疾类型为空错误
     */
    void typeBlankError();

    /**
     * 手机为空错误
     */
    void telBlankError();

    /**
     * 邮箱为空错误
     */
    void emailBlankError();

    /**
     * 入学年月为空错误
     */
    void eduExpAdmissionError(int id);

    /**
     * 毕业年月为空错误
     */
    void eduExpGraduationError(int id);

    /**
     * 教育经历学校为空错误
     */
    void eduExpSchoolBlankError(int id);

    /**
     * 教育经历专业为空错误
     */
    void eduExpMajorBlankError(int id);

    /**
     * 就职年月为空错误
     */
    void jobExpInaugurationBlankError(int id);

    /**
     * 离职年月为空错误
     */
    void jobExpDimissionBlankError(int id);

    /**
     * 工作经历公司为空错误
     */
    void jobExpCompanyBlankError(int id);

    /**
     * 工作经历职位为空错误
     */
    void jobExpPositionBlankError(int id);

    /**
     * 期待工作职位为空错误
     */
    void expectJobPositionBlankError();

    /**
     * 期待工作薪水为空错误
     */
    void expectJobSalaryBlankError();

    /**
     * 期待工作地址为空错误
     */
    void expectJobLocationBlankError();

    void close();

    void save();

    void load(TextCVBean textCVBean);

}
