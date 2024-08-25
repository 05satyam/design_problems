class Error:
    def __init__(self, line_num:int, log:str, timestamp:str=None):
        self.line_num=line_num
        self.log=log
        self.timestamp=timestamp

    def __str__(self):
        return f"[line numer:{self.line_num} - timestamp:{self.timestamp}] - log_message:{self.log}" if self.timestamp else f"[line numer:{self.line_num} - log_message:{self.log}"