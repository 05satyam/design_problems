from error_extractor import ErrorExtractor

class LogReader:
    def __init__(self, file_path:str):
        self.file_path=file_path
        self.error_extract_obj=ErrorExtractor()

    def read_log_file(self)->list:
        try:
            with open(self.file_path, 'r') as file:
                log_lines=file.readlines()
                return log_lines
        except Exception as e:
            print(f"exception occured : {e} ")

    def get_errors(self) -> list:
        try:
            log_lines=self.read_log_file()
            error=self.error_extract_obj.extract_errors(log_lines)
        except Exception as e:
            print(f"exception occured : {e}")
