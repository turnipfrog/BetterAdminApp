package com.example.betteradminapp.data

import android.content.Context
import com.example.betteradminapp.data.local.BetterAdminDatabase
import com.example.betteradminapp.data.local.OfflineCourseRepository
import com.example.betteradminapp.data.local.OfflineEnrollmentRepository
import com.example.betteradminapp.data.local.OfflineEventRepository
import com.example.betteradminapp.data.local.OfflineMessageRepository
import com.example.betteradminapp.data.local.OfflinePupilRepository
import com.example.betteradminapp.data.local.OfflineTeacherRepository

interface AppContainer {
    val pupilRepository: PupilRepository
    val teacherRepository: TeacherRepository
    val courseRepository: CourseRepository
    val enrollmentRepository: EnrollmentRepository
    val messageRepository: MessageRepository
    val eventRepository: EventRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val pupilRepository: PupilRepository by lazy {
        OfflinePupilRepository(BetterAdminDatabase.getDatabase(context).pupilDao())
    }
    override val teacherRepository: TeacherRepository by lazy {
        OfflineTeacherRepository(BetterAdminDatabase.getDatabase(context).teacherDao())
    }
    override val courseRepository: CourseRepository by lazy {
        OfflineCourseRepository(BetterAdminDatabase.getDatabase(context).courseDao())
    }
    override val enrollmentRepository: EnrollmentRepository by lazy {
        OfflineEnrollmentRepository(BetterAdminDatabase.getDatabase(context).enrollmentDao())
    }
    override val messageRepository: MessageRepository by lazy {
        OfflineMessageRepository(BetterAdminDatabase.getDatabase(context).messageDao())
    }
    override val eventRepository: EventRepository by lazy {
        OfflineEventRepository(BetterAdminDatabase.getDatabase(context).eventDao())
    }
}