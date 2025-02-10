# FIX Tag Occurence Tester

This Java program takes in FIX logs and returns what tags appear in it

Example usage:
```
$ java TagChecker TESTSESSION.message.log
Processing file: TESTSESSION.message.log
Processing...

Message Type: 0 - Heartbeat
Tags appearing in this message type:
Tag 10 (CheckSum): 6 occurrences
Tag 34 (MsgSeqNum): 6 occurrences
Tag 35 (MsgType): 6 occurrences
Tag 49 (SenderCompID): 6 occurrences
Tag 52 (SendingTime): 6 occurrences
Tag 56 (TargetCompID): 6 occurrences

Message Type: 8 - Execution Report
Tags appearing in this message type:
Tag 10 (CheckSum): 6 occurrences
Tag 11 (ClOrdID): 6 occurrences
Tag 150 (ExecType): 6 occurrences
Tag 17 (ExecID): 6 occurrences
Tag 31 (LastPx): 4 occurrences
Tag 32 (LastQty): 4 occurrences
Tag 34 (MsgSeqNum): 6 occurrences
Tag 35 (MsgType): 6 occurrences
Tag 38 (OrderQty): 6 occurrences
Tag 39 (OrdStatus): 6 occurrences
Tag 44 (Price): 6 occurrences
Tag 49 (SenderCompID): 6 occurrences
Tag 52 (SendingTime): 6 occurrences
Tag 54 (Side): 6 occurrences
Tag 55 (Symbol): 6 occurrences
Tag 56 (TargetCompID): 6 occurrences

Message Type: A - Logon
Tags appearing in this message type:
Tag 10 (CheckSum): 1 occurrences
Tag 108 (HeartBtInt): 1 occurrences
Tag 34 (MsgSeqNum): 1 occurrences
Tag 35 (MsgType): 1 occurrences
Tag 49 (SenderCompID): 1 occurrences
Tag 52 (SendingTime): 1 occurrences
Tag 56 (TargetCompID): 1 occurrences
Tag 98 (EncryptMethod): 1 occurrences
```
