package com.rest.api.service;

import com.rest.api.entity.User;
import com.rest.api.model.response.CommonResponse;
import com.rest.api.model.response.CommonResult;
import com.rest.api.model.response.ListResult;
import com.rest.api.model.response.SingleResult;
import com.rest.api.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final UserJpaRepo userJpaRepo;


    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();

        result.setData(data);
        setSuccessResult(result);

        return result;
    }

    @Transactional
    public <T> SingleResult<T> update(long msrl ,String uid, String name) {
        SingleResult<T> result = new SingleResult<>();

       User user =  userJpaRepo.findById(msrl)
                    .orElseThrow(() -> new IllegalArgumentException("회원정보가없어요"));

       user.update(uid,name);

        setSuccessResult(result);

        return result;
    }

    public <T> ListResult<T> getListResult(List<T> list) {

        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);

        return result;
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    private <T> void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    public CommonResult getFailResult(int code , String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
