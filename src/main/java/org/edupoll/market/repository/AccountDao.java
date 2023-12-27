package org.edupoll.market.repository;

import org.edupoll.market.model.Account;

public interface AccountDao {
	public int insert(Account account);

	public Account findById(String id);

	public int update(Account account);
}
