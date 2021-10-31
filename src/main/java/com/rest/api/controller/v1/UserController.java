package com.rest.api.controller.v1;

import com.rest.api.advice.exception.CUserNotFoundException;
import com.rest.api.entity.User;
import com.rest.api.model.response.CommonResult;
import com.rest.api.model.response.ListResult;
import com.rest.api.model.response.SingleResult;
import com.rest.api.repository.UserJpaRepo;
import com.rest.api.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController //결과값 json반환
@RequestMapping("/v1")
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation("회원조회")
    @GetMapping("/user")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userJpaRepo.findAll());
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원ID", required = true) @PathVariable long msrl, @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        // 결과데이터가 단일건인경우 getBasicResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userJpaRepo.findById(msrl).orElseThrow(CUserNotFoundException::new));
    }


    @ApiOperation("회원입력저장")
    @PostMapping("/user")
    public SingleResult<User> save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
                                   @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

//    @ApiOperation("회원입력저장2")
//    @PostMapping("/user2")
//    public User save2(@ApiParam(value = "회원엔티티", required = true) @RequestParam User user2) {
//        User user = User.builder()
//                .uid(user2.getUid())
//                .name(user2.getName())
//                .build();
//
//        return userJpaRepo.save(user);
//    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation("회원입력수정")
    @PutMapping("/user")
    public SingleResult<User> update(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
                                     @ApiParam(value = "회원이름", required = true) @RequestParam String name,
                                     @ApiParam(value = "회원아이디", required = true) @RequestParam Long msrl
    ) {
        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation("회원입력수정")
    @PutMapping("/user2")
    public CommonResult update2(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
                                     @ApiParam(value = "회원이름", required = true) @RequestParam String name,
                                     @ApiParam(value = "회원아이디", required = true) @RequestParam Long msrl
    ) {

        return responseService.update(msrl,uid,name);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation("회원입력삭제")
    @DeleteMapping("/user/{msrl}")
    public CommonResult delete(@ApiParam(value = "회원번호", required = true) @RequestParam Long msrl) {

        userJpaRepo.deleteById(msrl);

        return responseService.getSuccessResult();
    }

}
