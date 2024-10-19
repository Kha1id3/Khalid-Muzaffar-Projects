import unittest
from experta import *
from facts import CartridgeType, ErrorMessage, NetworkSettings, PaperType, PrintheadStatus, TroubleshootingStep, InkLevel, PaperTrayStatus, ConnectionType, UserAction
from rules import PrinterKnowledgeEngine

class TestPrinterKnowledgeEngine(unittest.TestCase):

    def setUp(self):
        self.engine = PrinterKnowledgeEngine()
        self.engine.reset()

    def test_paper_jam_with_empty_tray(self):
        self.engine.declare(ErrorMessage(message='Paper Jam'))
        self.engine.declare(PaperTrayStatus(status='Empty'))
        self.engine.run()
        steps = [fact['step'] for fact in self.engine.facts.values() if isinstance(fact, TroubleshootingStep)]
        self.assertIn("Refill the paper tray before clearing the jam.", steps)

    def test_print_quality_issue_with_low_ink(self):
        self.engine.declare(ErrorMessage(message='Print Quality Issue'))
        self.engine.declare(InkLevel(level='Low'))
        self.engine.run()
        steps = [fact['step'] for fact in self.engine.facts.values() if isinstance(fact, TroubleshootingStep)]
        self.assertIn("Replace the ink cartridges due to low ink levels.", steps)

    def test_no_connection_wireless(self):
        self.engine.declare(ErrorMessage(message='No Connection'))
        self.engine.declare(ConnectionType(type='Wireless'))
        self.engine.run()
        steps = [fact['step'] for fact in self.engine.facts.values() if isinstance(fact, TroubleshootingStep)]
        self.assertIn("Ensure the printer is within range of the router.", steps)
        self.assertIn("Check for wireless interference.", steps)

    def test_printer_restart(self):
        self.engine.declare(UserAction(action='Restart Printer'))
        self.engine.run()
        steps = [fact['step'] for fact in self.engine.facts.values() if isinstance(fact, TroubleshootingStep)]
        self.assertIn("Power cycle the printer.", steps)
        self.assertIn("Wait for the printer to complete its startup sequence.", steps)
    
    def test_print_quality_issue_generic_cartridge(self):
        self.engine.declare(ErrorMessage(message='Print Quality Issue'))
        self.engine.declare(CartridgeType(type='Generic'))
        self.engine.run()
        steps = [fact['step'] for fact in self.engine.facts.values() if isinstance(fact, TroubleshootingStep)]
        self.assertIn("Consider replacing with genuine HP cartridges for better quality.", steps)

    def test_misaligned_printhead(self):
        self.engine.declare(PrintheadStatus(status='Misaligned'))
        self.engine.declare(ErrorMessage(message='Print Quality Issue'))
        self.engine.run()
        steps = [fact['step'] for fact in self.engine.facts.values() if isinstance(fact, TroubleshootingStep)]
        self.assertIn("Align the printhead using the printer software.", steps)

    def test_ip_conflict(self):
        self.engine.declare(ErrorMessage(message='No Connection'))
        self.engine.declare(NetworkSettings(status='IP Conflict'))
        self.engine.run()
        steps = [fact['step'] for fact in self.engine.facts.values() if isinstance(fact, TroubleshootingStep)]
        self.assertIn("Check the IP settings and resolve any conflicts.", steps)

    def test_glossy_paper_jam(self):
        self.engine.declare(ErrorMessage(message='Paper Jam'))
        self.engine.declare(PaperType(type='Glossy'))
        self.engine.run()
        steps = [fact['step'] for fact in self.engine.facts.values() if isinstance(fact, TroubleshootingStep)]
        self.assertIn("Ensure glossy paper is not curled and is loaded correctly.", steps)








if __name__ == '__main__':
    unittest.main()
