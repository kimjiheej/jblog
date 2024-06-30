package com.poscodx.jblog.repository;

import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poscodx.jblog.security.UserDetailsImpl;
import com.poscodx.jblog.vo.UserVo;



@Repository
public class UserRepository {

private SqlSession sqlSession;
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
	}

	public UserVo findByIdAndPassword(String id, String password) {
		return sqlSession.selectOne(
				"user.findByIdAndPassword",
				Map.of("id", id, "password", password));
	}


	public <R> R findBySecurityId(String id, Class<R> resultType) {
		FindByIdResultHandler<R> findByIdResultHandler = new FindByIdResultHandler<>(resultType);
		sqlSession.select("user.findBySecurityId", id, findByIdResultHandler);

		return findByIdResultHandler.result;
	}
	
	private class FindByIdResultHandler<R> implements ResultHandler<Map<String, Object>> {
		private R result;
		private Class<R> resultType;

		FindByIdResultHandler(Class<R> resultType) {
			this.resultType = resultType;
		}

		@Override
		public void handleResult(ResultContext<? extends Map<String, Object>> resultContext) {
			Map<String, Object> resultMap = resultContext.getResultObject();
			result = new ObjectMapper().convertValue(resultMap, resultType);
		}
	}
	
	
	
	
	
}
