from experta import *
from facts import CartridgeType, ErrorMessage, NetworkSettings, PaperType, PrintheadStatus, TroubleshootingStep, InkLevel, PaperTrayStatus, ConnectionType, UserAction

class PrinterKnowledgeEngine(KnowledgeEngine):

    @Rule(ErrorMessage(message='Paper Jam'))
    def handle_paper_jam(self):
        self.declare(TroubleshootingStep(step="Remove the input tray and check for jammed paper."))
        self.declare(TroubleshootingStep(step="Check the print zone for jammed paper."))
        self.declare(TroubleshootingStep(step="Check the rear of the printer for jammed paper."))

    @Rule(ErrorMessage(message='Print Quality Issue'))
    def handle_print_quality_issue(self):
        self.declare(TroubleshootingStep(step="Check ink levels and replace cartridges if necessary."))
        self.declare(TroubleshootingStep(step="Align the printhead."))
        self.declare(TroubleshootingStep(step="Clean the printhead."))

    @Rule(ErrorMessage(message='No Connection'))
    def handle_no_connection(self):
        self.declare(TroubleshootingStep(step="Check the wireless connection."))
        self.declare(TroubleshootingStep(step="Restart the router and printer."))
        self.declare(TroubleshootingStep(step="Check for firewall issues."))

    @Rule(AND(ErrorMessage(message='Paper Jam'), PaperTrayStatus(status='Empty')))
    def handle_paper_jam_with_empty_tray(self):
        self.declare(TroubleshootingStep(step="Refill the paper tray before clearing the jam."))

    @Rule(AND(ErrorMessage(message='Print Quality Issue'), InkLevel(level='Low')))
    def handle_print_quality_issue_with_low_ink(self):
        self.declare(TroubleshootingStep(step="Replace the ink cartridges due to low ink levels."))

    @Rule(AND(ErrorMessage(message='No Connection'), ConnectionType(type='Wireless')))
    def handle_no_connection_wireless(self):
        self.declare(TroubleshootingStep(step="Ensure the printer is within range of the router."))
        self.declare(TroubleshootingStep(step="Check for wireless interference."))

    @Rule(UserAction(action='Restart Printer'))
    def handle_restart_printer(self):
        self.declare(TroubleshootingStep(step="Power cycle the printer."))
        self.declare(TroubleshootingStep(step="Wait for the printer to complete its startup sequence."))
    
    @Rule(AND(ErrorMessage(message='Print Quality Issue'), CartridgeType(type='Generic')))
    def handle_print_quality_issue_generic_cartridge(self):
        self.declare(TroubleshootingStep(step="Consider replacing with genuine HP cartridges for better quality."))

    @Rule(AND(PrintheadStatus(status='Misaligned'), ErrorMessage(message='Print Quality Issue')))
    def handle_misaligned_printhead(self):
        self.declare(TroubleshootingStep(step="Align the printhead using the printer software."))

    @Rule(AND(ErrorMessage(message='No Connection'), NetworkSettings(status='IP Conflict')))
    def handle_ip_conflict(self):
        self.declare(TroubleshootingStep(step="Check the IP settings and resolve any conflicts."))

    @Rule(AND(ErrorMessage(message='Paper Jam'), PaperType(type='Glossy')))
    def handle_glossy_paper_jam(self):
        self.declare(TroubleshootingStep(step="Ensure glossy paper is not curled and is loaded correctly."))