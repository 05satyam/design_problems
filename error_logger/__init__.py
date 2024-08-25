from read_logs import LogReader

if __name__=="__main__":
    log_reader = LogReader("path-to-logs")
    errors = log_reader.get_errors()
    for error in errors:
        print(error)