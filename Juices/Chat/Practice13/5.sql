select count(*), messages.user_id FROM messages group by messages.user_id HAVING count(*) > 3;