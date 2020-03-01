package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.AlbumAlreadyExistException;
import com.vinyl.model.WriteOff;

import java.util.List;

public interface WriteOffService {

	WriteOff getByNum(Integer num);

	Integer save(WriteOff writeOff) throws AlbumAlreadyExistException;

	List<WriteOff> searchWriteOffs(SearchDto searchDto);

	void update(WriteOff writeOff, Integer writeOffNum);
}
