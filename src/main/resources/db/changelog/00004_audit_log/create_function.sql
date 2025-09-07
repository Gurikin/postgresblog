CREATE FUNCTION log_posts_change()
    RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO postgres_blog.audit_log
    VALUES (NOW(), table_name, OLD.*, NEW.*);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER log_changes
    AFTER UPDATE ON postgres_blog.audit_log
    FOR EACH ROW EXECUTE FUNCTION
    log_posts_change();