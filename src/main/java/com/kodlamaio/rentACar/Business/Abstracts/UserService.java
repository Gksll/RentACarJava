package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.user.CreateUserRequest;
import com.kodlamaio.rentACar.Business.Requests.user.DeleteUserRequest;
import com.kodlamaio.rentACar.Business.Requests.user.UpdateUserRequest;
import com.kodlamaio.rentACar.Business.Responces.user.GetAllUserResponse;
import com.kodlamaio.rentACar.Business.Responces.user.GetUserResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.User;

public interface UserService {
	Result add(CreateUserRequest createUserRequest);

	Result update(UpdateUserRequest updateUserRequest);

	Result delete(DeleteUserRequest deleteUserRequest);

	DataResult<User> getById(GetUserResponse getUserResponse);

	DataResult<List<GetAllUserResponse>> getAll();
	
	DataResult<List<GetAllUserResponse>> getAll(Integer pageNo,Integer pageSize);
	
	Result CheckIfRealPerson(User user);
}
