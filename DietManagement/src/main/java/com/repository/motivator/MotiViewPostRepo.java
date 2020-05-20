package com.repository.motivator;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.model.motivator.MotiViewPost;

public interface MotiViewPostRepo extends CrudRepository<MotiViewPost, Integer>
{
	MotiViewPost findByFrmAndDtm(String frm, String dtm);
	
	List<MotiViewPost> findByAud(String aud);
}
