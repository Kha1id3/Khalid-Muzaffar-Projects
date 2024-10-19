from experta import *
from facts import CartridgeType, ErrorMessage, NetworkSettings, PaperType, PrintheadStatus, TroubleshootingStep, InkLevel, PaperTrayStatus, ConnectionType, UserAction
from rules import PrinterKnowledgeEngine

def main():
    engine = PrinterKnowledgeEngine()
    engine.reset()

    # Example scenarios
    engine.declare(ErrorMessage(message='Paper Jam'))
    engine.declare(PaperTrayStatus(status='Empty'))
    engine.run()
    print("Handled Paper Jam with Empty Tray")

    engine.declare(ErrorMessage(message='Print Quality Issue'))
    engine.declare(InkLevel(level='Low'))
    engine.run()
    print("Handled Print Quality Issue with Low Ink")

    engine.declare(ErrorMessage(message='No Connection'))
    engine.declare(ConnectionType(type='Wireless'))
    engine.run()
    print("Handled No Connection (Wireless)")

    engine.declare(UserAction(action='Restart Printer'))
    engine.run()
    print("Handled Printer Restart")

    engine.declare(ErrorMessage(message='Print Quality Issue'))
    engine.declare(CartridgeType(type='Generic'))
    engine.run()
    print("Handled Print Quality Issue with Generic Cartridge")

    engine.declare(PrintheadStatus(status='Misaligned'))
    engine.declare(ErrorMessage(message='Print Quality Issue'))
    engine.run()
    print("Handled Misaligned Printhead")

    engine.declare(ErrorMessage(message='No Connection'))
    engine.declare(NetworkSettings(status='IP Conflict'))
    engine.run()
    print("Handled IP Conflict")

    engine.declare(ErrorMessage(message='Paper Jam'))
    engine.declare(PaperType(type='Glossy'))
    engine.run()
    print("Handled Glossy Paper Jam")

if __name__ == "__main__":
    main()

