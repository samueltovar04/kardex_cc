insert into products (name,description,count,price, createTime, updateTime)values('book', 'the best book', 100, 250, NOW(), NOW());
insert into products (name,description,count,price, createTime, updateTime)values('pencil', 'the best pencil', 999, 50, NOW(), NOW());
insert into products (name,description,count,price, createTime, updateTime)values('paper', 'the best paper A4', 0, 300, NOW(), NOW());

insert into customers (user_name,password,name,last_name,email,phone,createTime,updateTime)values('soderling','abc123','Emanuel','Sodero','sodero.emanuel@gmail.com','1127945540',NOW(),NOW());

insert into purchases (date,total,customer_id,createTime,updateTime)values(NOW(), 3000, 1, NOW(), NOW());

insert into purchase_details (count, price, total, product_id, purchase_id)values(10, 250, 2500, 1, 1);
insert into purchase_details (count, price, total, product_id, purchase_id)values(10, 50, 500, 2, 1);




