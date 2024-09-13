SELECT sr.id, sr.solution_id, sr.code, sr.success_or_not
FROM solution_record sr
JOIN user u ON sr.user_id = u.id
WHERE u.id = :userId;
