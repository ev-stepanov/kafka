create view BANK_ACCOUNT_INFO as
select b.uuid, b.first_name, b.last_name, a.city, b.created_date, floor(dbms_random.value(0,2)) BLACK_LISTED
from ADDRESS a
inner join BANK_ACCOUNT b on a.uuid=b.uuid;
