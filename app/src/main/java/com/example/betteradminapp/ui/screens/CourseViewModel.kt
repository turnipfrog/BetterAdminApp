package com.example.betteradminapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteradminapp.data.CourseRepository
import com.example.betteradminapp.data.EnrollmentRepository
import com.example.betteradminapp.data.TeacherRepository
import com.example.betteradminapp.data.UserPreferencesRepository
import com.example.betteradminapp.data.model.Course
import com.example.betteradminapp.data.model.Teacher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(
    val enrollmentRepository: EnrollmentRepository,
    val courseRepository: CourseRepository,
    val teacherRepository: TeacherRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private var pupilId: Int = -1

    init {
        setUserId()

//        viewModelScope.launch {
//            teacherRepository.insertTeacher(
//                Teacher(
//                    firstName = "Karen",
//                    lastName = "Klavermus",
//                    phoneNo = "80808080",
//                    email = "Klavermus@musik.dk"
//                )
//            )
//            teacherRepository.insertTeacher(
//                Teacher(
//                    firstName = "Sarah",
//                    lastName = "Saxogpapir",
//                    phoneNo = "98765432",
//                    email = "Saksepigen@musik.dk"
//                )
//            )
//            val teacherId1 = teacherRepository.getTeacherByEmailStream("Klavermus@musik.dk").firstOrNull()?.id ?: 0
//            val teacherId2 = teacherRepository.getTeacherByEmailStream("Saksepigen@musik.dk").firstOrNull()?.id ?: 0
//
//            courseRepository.insertCourse(
//                Course(
//                    courseName = "Klaver",
//                    maxEnrolled = 30,
//                    startDate = Date(2024, 5, 13, 18, 0),
//                    teacherId = teacherId1,
//                    classroomName = "B2.01"
//                )
//            )
//            courseRepository.insertCourse(
//                Course(
//                    courseName = "Klaver",
//                    maxEnrolled = 30,
//                    startDate = Date(2024, 5, 18, 18, 0),
//                    teacherId = teacherId1,
//                    classroomName = "B2.01"
//                )
//            )
//            courseRepository.insertCourse(
//                Course(
//                    courseName = "Klaver",
//                    maxEnrolled = 30,
//                    startDate = Date(2024, 5, 25, 18, 0),
//                    teacherId = teacherId1,
//                    classroomName = "B2.01"
//                )
//            )
//            courseRepository.insertCourse(
//                Course(
//                    courseName = "Klaver",
//                    maxEnrolled = 30,
//                    startDate = Date(2024, 6, 1, 18, 0),
//                    teacherId = teacherId1,
//                    classroomName = "B2.01"
//                )
//            )
//            courseRepository.insertCourse(
//                Course(
//                    courseName = "Saxofon",
//                    maxEnrolled = 30,
//                    startDate = Date(2024, 5, 17, 17, 0),
//                    teacherId = teacherId2,
//                    classroomName = "A1.14"
//                )
//            )
//            courseRepository.insertCourse(
//                Course(
//                    courseName = "Saxofon",
//                    maxEnrolled = 30,
//                    startDate = Date(2024, 5, 24, 17, 0),
//                    teacherId = teacherId2,
//                    classroomName = "A1.14"
//                )
//            )
//            courseRepository.insertCourse(
//                Course(
//                    courseName = "Saxofon",
//                    maxEnrolled = 30,
//                    startDate = Date(2024, 5, 31, 17, 0),
//                    teacherId = teacherId2,
//                    classroomName = "A1.14"
//                )
//            )
//            val courseIdList: List<Int> = courseRepository.getAllCoursesStream().first().map { course -> course.id }
//            courseIdList.forEach {
//                enrollmentRepository.insertEnrollment(
//                    Enrollment(
//                        pupilId = pupilId,
//                        courseId = it
//                    )
//                )
//            }
//        }
    }

    val courseUiState: StateFlow<CourseUiState> =
        courseRepository.getCoursesByPupilIdStream(pupilId).map { CourseUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CourseUiState()
            )

    val teacherState: StateFlow<TeacherState> =
        teacherRepository.getAllTeachersStream().map { TeacherState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TeacherState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private fun setUserId() {
        viewModelScope.launch {
            pupilId = userPreferencesRepository.readUserId() ?: -1
        }
    }

    fun getTeacherNameById(teacherId: Int): String {
        val teacher = teacherState.value.teachers.find { teacher -> teacher.id == teacherId}
        return "${teacher?.firstName} ${teacher?.lastName}"
    }

    fun getTeacherPhoneNoById(teacherId: Int): String {
        val teacher = teacherState.value.teachers.find { teacher -> teacher.id == teacherId}
        return "${teacher?.phoneNo}"
    }

    fun getTeacherEmailById(teacherId: Int): String {
        val teacher = teacherState.value.teachers.find { teacher -> teacher.id == teacherId}
        return "${teacher?.email}"
    }
}

data class CourseUiState(val courses: List<Course> = listOf())
data class TeacherState(val teachers: List<Teacher> = listOf())