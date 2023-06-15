package com.example.user.service;


import com.example.user.dao.UserDAO;
import com.example.user.vo.UserVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;


//    public UserServiceImpl(UserRepository userRepository) {
//        super();
//        this.userRepository = userRepository;
//    }

    @Override
    public void join(UserVO userVO) {
        userDAO.insert(userVO);
    }

    @Override
    public boolean login(String id, String pwd) {
        //입력된 id로 유저 객체를 받아옴
        //유저 정보를 가져오는데 실패하면 예외를 던짐
        UserVO userVO = getUser(id);
        if (userVO.getId() == "" || userVO.getId() == null ||
                userVO.getPwd() =="" || userVO.getPwd() == null) {
            return false;
        }
        //id==pwd 일치하는지
		return pwd.equals(userVO.getPwd());
	}

    @Override
    public UserVO getUser(String id) {
        return userDAO.getById(id);
    }
}
