--получить информацию обо всех студентах школы Хогвартс вместе с названиями факультетов--
SELECT st.name, st.age, ft.name
FROM student st
INNER JOIN faculty ft ON st.ft_id = ft.id;
--получить только тех студентов, у которых есть аватарки--
SELECT st.name
FROM student st
INNER JOIN avatar av ON st.id = av.student_id
WHERE st.avatar = 1;