/**
 * 
 */
package com.dineshonjava.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineshonjava.model.User;
import com.dineshonjava.utils.UserRowMapper;

/**
 * @author Dinesh.Rajput
 *
 */
@Service
public class UserService {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
 
    @Transactional(readOnly=true)
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", 
                new UserRowMapper());
    }
 
    @Transactional(readOnly=true)
    public User findUserById(int id) {
        return jdbcTemplate.queryForObject(
            "select * from users where userId=?",
            new Object[]{id}, new UserRowMapper());
    }
 
    public User create(final User user) 
    {
        final String sql = "insert into users(userId,userName,userEmail,address) values(?,?,?,?)";
 
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user.getUserId());
                ps.setString(2, user.getUserName());
                ps.setString(3, user.getUserEmail());
                ps.setString(4, user.getAddress());
                return ps;
            }
        }, holder);
 
        int newUserId = holder.getKey().intValue();
        user.setUserId(newUserId);
        return user;
    }
}
