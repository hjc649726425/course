package com.hjc.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//rolling back on RuntimeException and Error but not on checked exceptions
//当程序发生 RuntimeException 和 Error 的这两种异常的时候事务会回滚，但是如果发生了checkedExcetions ，
// 如fileNotfundException 则不会回滚，所以 rollbackFor = Exception.class 这个一定要加！
@EnableTransactionManagement
@Configuration
public class TransactionManagementConfig {
	
}