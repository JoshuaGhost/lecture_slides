use MovieDB;

select name, birth_date, birth_country, birth_location from person where height>170 and sex='f' and birth_location='San Francisco, California' and birth_country='USA';

select unique currency from budget where budget>900000000;

select unique aka from aka,production where (title like '% 4' or title like '% IV') and country='Germany' and aka.PRODUCTION=production.PRODUCTION;

select name as Name, realname as echter_Name, birth_date as Geburtsdatum, height from person where sex='m' and name like '%, Spencer' and birth_date is not null and death_date is null order by name asc;

select name, to_char(sysdate, 'fmYYYY')-to_char(birth_date, 'fmYYYY') as age from person where BIRTH_COUNTRY = 'Germany' and to_char(birth_date, 'fmDD MON') = to_char(sysdate, 'fmDD MON');

select unique substr(quote.quote, 1, instr(quote.quote, ':')-1) name from quote where not substr(quote.quote, 1, instr(quote.quote, ':')-1) = 'Homer Simpson' and quote like '%Homer Simpson%' order by name asc;

SELECT aka FROM aka WHERE aka LIKE '%' || country || '%';

SELECT production, DECODE(country, 'West Germany', 'Germany','East Germany', 'Germany', country) FROM release WHERE year = 1984 AND month = 7;

select name "Name" from person;
SELECT name "Name", TO_CHAR(birth_date, 'YYYY'), TO_CHAR(death_date, 'YYYY'), birth_location "Geburts- und Todesort" FROM person WHERE birth_location = death_location ORDER BY birth_date ASC;