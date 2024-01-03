package org.edupoll.market.repository;

import java.util.List;

import org.edupoll.market.model.Pick;

public interface PickDao {
	public int save(Pick pick);
	
	public int deleteById(int id);
	
	public int deleteByOwnderAndTarget(Pick pick);
	
	public int countByTarget(int targetProductId);
	
	public int countByOwnerAndTarget(Pick pick);
	
	public List<Pick> findByOwner(String ownerAccountId);
}
