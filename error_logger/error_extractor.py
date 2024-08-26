class ErrorExtractor:
    def __init__(self, error_type:list=None):
        self.error_type= error_type if error_type  else ["ERROR", "DEBUG", "EXCEPTION"]

    def extract_errors(self, log_lines:list=None)->list:
        error=[]
        if log_lines:
            for line_num, log in enumerate(log_lines, start=1):
                if any(keyword in log for keyword in self.error_type):
                    log_timestamps=self.extract_timestamp(line)
                    error.append(Error(line_num, logs.strip(), log_timestamps))

        return error

    def extract_timestamp(self, line):
        return line.split()[0] if len(line.split())>0 else None
