# Unique offering constraint

**context** Location<br>
**inv**: self.Lesson **->** **select**( l<sub>1</sub>, l<sub>2</sub>: Lesson | l<sub>1</sub>.timeSlot.dayOfWeek = l<sub>2</sub>.timeSlot.dayOfWeek) <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **->** **forAll**( l<sub>1</sub>, l<sub>2</sub>: Lesson | l<sub>1</sub> <> l<sub>2</sub> 
 **implies** <br>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;l<sub>1</sub>.timeSlot.end <= l<sub>2</sub>.timeSlot.start **or** <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;l<sub>1</sub>.timeSlot.start >= l<sub>2</sub>.timeSlot.end )

*note : Since an Offering has a lesson and there is a one to one correspondance between offering and lesson, this OCL works even though we don't explicitly use Offering*

# Underage Guardian Requirement

**context** UnderageClient  
**inv**: self.guardian.oclIsTypeOf(Client)

# Instructor City Availability

**context** Offering <br>
**inv**: self.Instructor.cities -> **includes**( self.Lesson.city )

# Client Booking conflicts

