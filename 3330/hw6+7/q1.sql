select "Name" from STUDENT where Class=4 AND Major="CS";


select Course_name from COURSE
where Instructor="King"
AND "Year" between 07 AND 08;



SELECT SECTION.Course_number, SECTION.Semester, SECTION.Year, COUNT(GRADE_REPORT.Student_number) AS NumberOfStudents
FROM SECTION, GRADE_REPORT
WHERE SECTION.Section_identifier = GRADE_REPORT.Section_identifier
AND SECTION.Instructor = 'King'
GROUP BY SECTION.Course_number, SECTION.Semester, SECTION.Year;


SELECT Student.Name, COURSE.Course_name, COURSE.Course_number, COURSE.Credit_hours, SECTION.Semester, SECTION.Year, GRADE_REPORT.Grade
FROM Student, COURSE, SECTION, GRADE_REPORT
WHERE Student.Student_number = GRADE_REPORT.Student_number
AND GRADE_REPORT.Section_identifier = SECTION.Section_identifier
AND SECTION.Course_number = COURSE.Course_number
AND Student.Major = 'CS'
AND Student.Class = 4;



SELECT Student.Name, Student.Major
FROM Student
WHERE NOT EXISTS (
    SELECT *
    FROM GRADE_REPORT, SECTION
    WHERE Student.Student_number = GRADE_REPORT.Student_number
    AND GRADE_REPORT.Section_identifier = SECTION.Section_identifier
    AND GRADE_REPORT.Grade <> 'A'
);


SELECT Student.Name, Student.Major
FROM Student
WHERE NOT EXISTS (
    SELECT *
    FROM GRADE_REPORT, SECTION
    WHERE Student.Student_number = GRADE_REPORT.Student_number
    AND GRADE_REPORT.Section_identifier = SECTION.Section_identifier
    AND GRADE_REPORT.Grade = 'A'
);
