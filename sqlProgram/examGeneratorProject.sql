CREATE SCHEMA Exam_Generator;
USE Exam_Generator;
CREATE TABLE answerTable
(answerText VARCHAR(200) NOT NULL,
PRIMARY KEY (answerText))
ENGINE = InnoDB;

CREATE TABLE questionTable
(questionID INT NOT NULL,
questionText VARCHAR(200),
questionType VARCHAR(200) NOT NULL,
PRIMARY KEY (questionID,questionType))
ENGINE = InnoDB;

CREATE TABLE closeQuestionTable
(CQID INT NOT NULL,
numOfAnswers INT,
PRIMARY KEY (CQID),
FOREIGN KEY (CQID) REFERENCES questionTable(questionID))
ENGINE = InnoDB;

CREATE TABLE openQuestionTable
(OQID INT NOT NULL,
answerText VARCHAR(200),
PRIMARY KEY (OQID),
FOREIGN KEY (OQID) REFERENCES questionTable(questionID),
FOREIGN KEY (answerText) REFERENCES answerTable(answerText))
ENGINE = InnoDB;

CREATE TABLE closeQuestionsAnswers
(CQID INT NOT NULL,
answerText VARCHAR(200),
indication INT,
PRIMARY KEY (CQID,answerText),
FOREIGN KEY (CQID) REFERENCES questionTable(questionId),
FOREIGN KEY (answerText) REFERENCES answerTable(answerText))
ENGINE = InnoDB;

CREATE TABLE testTable
(testID INT NOT NULL,
numOfQuestions INT DEFAULT 0,
testType VARCHAR(200) NOT NULL,
PRIMARY KEY (testID))
ENGINE = InnoDB;

CREATE TABLE testContains
(TID INT NOT NULL, 
TQID INT NOT NULL, 
PRIMARY KEY (TID,TQID),
FOREIGN KEY (TID) REFERENCES testTable(testId),
FOREIGN KEY (TQID) REFERENCES questionTable(questionId))
ENGINE = InnoDB;

CREATE TABLE closeQuestionsInTest
(TID INT NOT NULL,
TCQID INT NOT NULL,
TAnsText VARCHAR(200) NOT NULL,
TAnsInd INT,
PRIMARY KEY (TID ,TCQID,TAnsText),
FOREIGN KEY (TID) REFERENCES  testTable(testId),
FOREIGN KEY (TCQID) REFERENCES questionTable(questionId),
FOREIGN KEY (TAnsText) REFERENCES answerTable(answerText))
ENGINE = InnoDB;

