USE Exam_Generator;

INSERT INTO answerTable VALUES
('Sunday'),
('Monday'),
('Friday'),
('Wednesday'),
('Saturday'),
('365 days'),
('April'),
('December'),
('July'),
('March'),
('January'),
('June'),
('August'),
('November'),
('There is more than one answer'),
('There is no correct answer');

INSERT INTO questionTable VALUES
(1,'what is the day of rest in Israel?','close'),
(2,'What is the day of rest in Israel?','open'),
(3,'How many days are there in a year?','open'),
(4,'What is the 12th month?','close'),
(5,'Which month is considered summer?','close');

INSERT INTO closeQuestionTable VALUES
(1,4),
(4,4),
(5,5);

INSERT INTO openQuestionTable VALUES
(2,'Saturday'),
(3,'365 days');

INSERT INTO closeQuestionsAnswers VALUES
(1,'Sunday',0),
(1,'Monday',0),
(1,'Friday',0),
(1,'Wednesday',0),
(4,'April',0),
(4,'December',1),
(4,'July',0),
(4,'March',0),
(5,'January',0),
(5,'June',1),
(5,'August',1),
(5,'November',0),
(5,'December',0);




