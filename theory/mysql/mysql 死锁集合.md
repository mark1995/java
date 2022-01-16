## insert ignore + select * from table where uid = #{uid} for update
1. insert ignore account (uid) values (101);
2. select * from table where uid = #{uid} for update
 	-	insert ignore 会先占用S(共享锁) 
 		+	insert ignore 先占用S锁，如果没有数据，插入成功，就会升级成X锁
 	-	select * from table where uid = #{uid} for update 会占用X锁
 	-	如果这个时候也有一个线程 也用了 insert ignore 占用了 S (共享锁)，然后也去 select for update 就会导致dead lock
 		
