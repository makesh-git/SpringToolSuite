package com.repository.admin;

import org.springframework.data.repository.CrudRepository;

import com.model.admin.AdminViewPost;

public interface AdminViewPostReop extends CrudRepository<AdminViewPost, Integer>
{
	AdminViewPost findByFrmAndDtm(String frm, String dtm);
	}
