package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.kodlamaio.rentACar.Business.Abstracts.UserService;
import com.kodlamaio.rentACar.Business.Requests.user.CreateUserRequest;
import com.kodlamaio.rentACar.Business.Requests.user.DeleteUserRequest;
import com.kodlamaio.rentACar.Business.Requests.user.UpdateUserRequest;
import com.kodlamaio.rentACar.Business.Responces.user.GetAllUserResponse;
import com.kodlamaio.rentACar.Business.Responces.user.GetUserResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.ErrorResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.Core.webservices.MernisServiceAdapter;
import com.kodlamaio.rentACar.DataAccess.Abstracts.UserRepository;
import com.kodlamaio.rentACar.Entities.Concretes.User;

@Service
public class UserManager implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private ModelMapperService mapper;

	private MernisServiceAdapter adapter;

	public UserManager(MernisServiceAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public Result add(CreateUserRequest createUserRequest) {
		User user = this.mapper.forRequest().map(createUserRequest, User.class);
		if (CheckIfRealPerson(user).isSuccess()) {
			this.userRepository.save(user);
			return new SuccessResult();
		} else {
			
			return new ErrorResult();
		}
//		this.userRepository.save(user);
//		return new SuccessResult();
	}

	@Override
	public Result update(UpdateUserRequest updateUserRequest) {
		User userToUpdate = this.mapper.forRequest().map(updateUserRequest, User.class);
		userRepository.save(userToUpdate);
		return new SuccessResult();
	}

	@Override
	public Result delete(DeleteUserRequest deleteUserRequest) {
		userRepository.deleteById(deleteUserRequest.getId());
		return new SuccessResult();
	}

	@Override
	public DataResult<User> getById(GetUserResponse getUserResponse) {
		User user = this.mapper.forResponce().map(getUserResponse, User.class);
		user = userRepository.findById(getUserResponse.getId()).get();
		return new SuccessDataResult<User>(user);
	}

	@Override
	public DataResult<List<GetAllUserResponse>> getAll() {
		List<User> users = this.userRepository.findAll();
		List<GetAllUserResponse> responce = users.stream()
				.map(item -> this.mapper.forResponce().map(item, GetAllUserResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllUserResponse>>(responce);
	}

	@Override
	public DataResult<List<GetAllUserResponse>> getAll(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<User> users = this.userRepository.findAll(pageable).getContent();
		List<GetAllUserResponse> responce = users.stream()
				.map(item -> this.mapper.forResponce().map(item, GetAllUserResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllUserResponse>>(responce);
	}

	@Override
	public Result CheckIfRealPerson(User user) {
		return new Result(adapter.CheckIfRealPerson(user));
	}

}
