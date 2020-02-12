package com.vinyl.service;

import com.vinyl.exception.AlbumAlreadyExistException;
import com.vinyl.model.Track;
import com.vinyl.model.WriteOff;

import java.util.List;

public interface WriteOffService {

	WriteOff getByNum(Integer num);

	Integer save(WriteOff writeOff) throws AlbumAlreadyExistException;

	List<WriteOff> searchWriteOffs(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
								   List<String> joins, String sorting, String order, Integer limit, Integer offset);

	void update(WriteOff writeOff, Integer writeOffNum);
}
