package com.hjc.server.service;

import com.hjc.server.domain.User;
import com.hjc.server.domain.UserExample;
import com.hjc.server.dto.UserDto;
import com.hjc.server.dto.PageDto;
import com.hjc.server.mapper.UserMapper;
import com.hjc.server.util.CopyUtil;
import com.hjc.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> userDtoList = CopyUtil.copyList(userList, UserDto.class);
        pageDto.setList(userDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(UserDto userDto) {
        User user = CopyUtil.copy(userDto, User.class);
        if (StringUtils.isEmpty(userDto.getId())) {
            this.insert(user);
        } else {
            this.update(user);
        }
    }

    /**
     * 新增
     */
    private void insert(User user) {
        user.setId(UuidUtil.getShortUuid());
        userMapper.insert(user);
    }

    /**
     * 更新
     */
    private void update(User user) {
        //userMapper.updateByPrimaryKey(user);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        //userMapper.deleteByPrimaryKey(id);
    }
}
