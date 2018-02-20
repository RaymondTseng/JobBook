package com.example.jobbook.base.contract.cv;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.EducationExpBean;
import com.example.jobbook.model.bean.JobExpBean;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.bean.TextCVBean;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2017/12/1.
 */

public interface TextCVContract {

    interface View extends IBaseView {
        /**
         * 成功
         */
        void success(PersonBean personBean);

        /**
         * 头像为空错误
         */
        void headBlankError();

        /**
         * 名字为空错误
         */
        void nameBlankError();

        /**
         * 求职状态为空错误
         */
        void statusBlankError();

        /**
         * 公司为空错误
         */
        void companyBlankError();

        /**
         * 职位为空错误
         */
        void positionBlankError();

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

        void load(TextCVBean textCVBean);
    }

    interface Presenter extends IBasePresenter<TextCVContract.View> {
        void basedInformationCheck(String head, String name, String sex, String status, String company, String position,
                                   String location, String type, String level, String haveCertification,
                                   String tel, String email, String expectJob,
                                   String expectSalary, String expectLocation);

        void educationExpCheck(List<EducationExpBean> educationExpBeanList);

        void jobExpCheck(List<JobExpBean> jobExpBeanList);

        void load();

    }
}
