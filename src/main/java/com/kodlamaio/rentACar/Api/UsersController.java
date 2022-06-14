package com.kodlamaio.rentACar.Api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kodlamaio.rentACar.Business.Abstracts.UserService;
import com.kodlamaio.rentACar.Business.Requests.user.CreateUserRequest;
import com.kodlamaio.rentACar.Business.Requests.user.DeleteUserRequest;
import com.kodlamaio.rentACar.Business.Requests.user.UpdateUserRequest;
import com.kodlamaio.rentACar.Business.Responces.user.GetAllUserResponse;
import com.kodlamaio.rentACar.Business.Responces.user.GetUserResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.ErrorResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Entities.Concretes.User;

import lombok.var;

@RestController
@RequestMapping("api/users")
public class UsersController {
	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateUserRequest createUserRequest) {
		var result = userService.add(createUserRequest);
		if (result.isSuccess()) {
			return new SuccessResult("doğru");
		} else {
			return new ErrorResult("yanlış");
		}
	}

	@PostMapping("/delete")
	public Result delete(DeleteUserRequest deleteUserRequest) {
		userService.delete(deleteUserRequest);
		return new SuccessResult();
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateUserRequest updateUserRequest) {
		userService.update(updateUserRequest);
		return new SuccessResult();
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllUserResponse>> GetAll() {
		return this.userService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<User> GetById(GetUserResponse getUserResponce) {
		return this.userService.getById(getUserResponce);
	}

	@GetMapping("/getallbypage")
	public DataResult<List<GetAllUserResponse>> getAll(@RequestParam int pageNo, int pageSize) {
		return this.userService.getAll(pageNo, pageSize);
	}
}
