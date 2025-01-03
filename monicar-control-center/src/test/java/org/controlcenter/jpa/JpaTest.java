package org.controlcenter.jpa;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.controlcenter.alarm.domain.Alarm;
import org.controlcenter.alarm.domain.AlarmStatus;
import org.controlcenter.alarm.infrastructure.jpa.AlarmJpaRepository;
import org.controlcenter.alarm.infrastructure.jpa.entity.AlarmEntity;
import org.controlcenter.company.domain.Company;
import org.controlcenter.company.domain.Department;
import org.controlcenter.company.domain.Manager;
import org.controlcenter.company.domain.Role;
import org.controlcenter.company.infrastructure.jpa.CompanyJpaRepository;
import org.controlcenter.company.infrastructure.jpa.DepartmentJpaRepository;
import org.controlcenter.company.infrastructure.jpa.ManagerJpaRepository;
import org.controlcenter.company.infrastructure.jpa.entity.CompanyEntity;
import org.controlcenter.company.infrastructure.jpa.entity.DepartmentEntity;
import org.controlcenter.company.infrastructure.jpa.entity.ManagerEntity;
import org.controlcenter.geoinfo.domain.CycleInfo;
import org.controlcenter.geoinfo.domain.GpsStatus;
import org.controlcenter.geoinfo.infrastructure.jpa.CycleInfoJpaRepository;
import org.controlcenter.geoinfo.infrastructure.jpa.entity.CycleInfoEntity;
import org.controlcenter.history.domain.DrivingHistory;
import org.controlcenter.history.infrastructure.jpa.DrivingHistoryJpaRepository;
import org.controlcenter.history.infrastructure.jpa.entity.DrivingHistoryEntity;
import org.controlcenter.notice.domain.Notice;
import org.controlcenter.notice.infrastructure.jpa.NoticeJpaRepository;
import org.controlcenter.notice.infrastructure.jpa.entity.NoticeEntity;
import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.domain.VehicleEventType;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.domain.VehicleType;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleEventJpaRepository;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleInformationJpaRepository;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleTypesJpaRepository;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleEventEntity;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleInformationEntity;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleTypeEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("[infrastructure 테스트] JPA 연결 테스트")
@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JpaTest {

	@Autowired
	private AlarmJpaRepository alarmJpaRepository;

	@Autowired
	private CompanyJpaRepository companyJpaRepository;

	@Autowired
	private DepartmentJpaRepository departmentJpaRepository;

	@Autowired
	private ManagerJpaRepository managerJpaRepository;

	@Autowired
	private CycleInfoJpaRepository cycleInfoJpaRepository;

	@Autowired
	private DrivingHistoryJpaRepository drivingHistoryJpaRepository;

	@Autowired
	private NoticeJpaRepository noticeJpaRepository;

	@Autowired
	private VehicleEventJpaRepository vehicleEventJpaRepository;

	@Autowired
	private VehicleInformationJpaRepository vehicleInformationJpaRepository;

	@Autowired
	private VehicleTypesJpaRepository vehicleTypesJpaRepository;

	@DisplayName("AlarmEntity 테스트")
	@Test
	void AlarmEntityTest() {
		// given
		Alarm alarm = Alarm.builder()
			.managerId(100L)
			.description("test description")
			.status(AlarmStatus.CHECKED)
			.deletedAt(null)
			.build();
		alarmJpaRepository.save(AlarmEntity.from(alarm));

		// when
		List<Alarm> Alarms = alarmJpaRepository.findAll().stream()
			.map(AlarmEntity::toDomain)
			.toList();

		// then
		assertThat(Alarms).hasSize(1);

		Alarm savedAlarm = Alarms.getFirst();

		assertAll(
			() -> assertThat(savedAlarm.getId()).isNotNull(),
			() -> assertThat(savedAlarm.getCreatedAt()).isNotNull(),
			() -> assertThat(savedAlarm.getUpdatedAt()).isNotNull(),
			() -> assertThat(savedAlarm.getManagerId()).isEqualTo(100L),
			() -> assertThat(savedAlarm.getDescription()).isEqualTo("test description"),
			() -> assertThat(savedAlarm.getStatus()).isEqualTo(AlarmStatus.CHECKED)
		);
	}

	@DisplayName("CompanyEntity 테스트")
	@Test
	void CompanyEntityTest() {
		// given
		Company company = Company.builder()
			.companyName("test company name")
			.businessRegistrationNumber("999")
			.deletedAt(null)
			.build();
		companyJpaRepository.save(CompanyEntity.from(company));

		// when
		List<Company> Companies = companyJpaRepository.findAll().stream()
			.map(CompanyEntity::toDomain)
			.toList();

		// then
		assertThat(Companies).hasSize(1);

		Company savedCompany = Companies.getFirst();

		assertAll(
			() -> assertThat(savedCompany.getId()).isNotNull(),
			() -> assertThat(savedCompany.getCreatedAt()).isNotNull(),
			() -> assertThat(savedCompany.getUpdatedAt()).isNotNull(),
			() -> assertThat(savedCompany.getCompanyName()).isEqualTo("test company name"),
			() -> assertThat(savedCompany.getBusinessRegistrationNumber()).isEqualTo("999")
		);
	}

	@DisplayName("DepartmentEntity 테스트")
	@Test
	void DepartmentEntityTest() {
		// given
		Department department = Department.builder()
			.companyId(999L)
			.departmentName("test department")
			.deletedAt(null)
			.build();
		departmentJpaRepository.save(DepartmentEntity.from(department));

		// when
		List<Department> Departments = departmentJpaRepository.findAll().stream()
			.map(DepartmentEntity::toDomain)
			.toList();

		// then
		assertThat(Departments).hasSize(1);

		Department savedDepartment = Departments.getFirst();

		assertAll(
			() -> assertThat(savedDepartment.getId()).isNotNull(),
			() -> assertThat(savedDepartment.getCreatedAt()).isNotNull(),
			() -> assertThat(savedDepartment.getUpdatedAt()).isNotNull(),
			() -> assertThat(savedDepartment.getCompanyId()).isEqualTo(999L),
			() -> assertThat(savedDepartment.getDepartmentName()).isEqualTo("test department")
		);
	}

	@DisplayName("ManagerEntity 테스트")
	@Test
	void ManagerEntityTest() {
		// given
		Manager manager = Manager.builder()
			.departmentId(999L)
			.email("test email")
			.loginId("test login id")
			.password("test password")
			.nickname("test nickname")
			.role(Role.ROLE_ADMIN)
			.lastLoginedAt(LocalDateTime.now())
			.deletedAt(null)
			.build();
		managerJpaRepository.save(ManagerEntity.from(manager));

		// when
		List<Manager> managers = managerJpaRepository.findAll().stream()
			.map(ManagerEntity::toDomain)
			.toList();

		// then
		assertThat(managers).hasSize(1);

		Manager savedManager = managers.getFirst();

		assertAll(
			() -> assertThat(savedManager.getId()).isNotNull(),
			() -> assertThat(savedManager.getCreatedAt()).isNotNull(),
			() -> assertThat(savedManager.getUpdatedAt()).isNotNull(),
			() -> assertThat(savedManager.getDepartmentId()).isEqualTo(999L),
			() -> assertThat(savedManager.getEmail()).isEqualTo("test email")
		);
	}

	@DisplayName("NoticeEntity 테스트")
	@Test
	void NoticeEntityTest() {
		// given
		Notice notice = Notice.builder()
			.title("test title")
			.content("test content")
			.imageUrl("test image url")
			.deletedAt(null)
			.build();
		noticeJpaRepository.save(NoticeEntity.from(notice));

		// when
		List<Notice> notices = noticeJpaRepository.findAll().stream()
			.map(NoticeEntity::toDomain)
			.toList();

		// then
		assertThat(notices).hasSize(1);

		Notice savedNotice = notices.getFirst();

		assertAll(
			() -> assertThat(savedNotice.getId()).isNotNull(),
			() -> assertThat(savedNotice.getCreatedAt()).isNotNull(),
			() -> assertThat(savedNotice.getUpdatedAt()).isNotNull(),
			() -> assertThat(savedNotice.getTitle()).isEqualTo("test title"),
			() -> assertThat(savedNotice.getContent()).isEqualTo("test content"),
			() -> assertThat(savedNotice.getImageUrl()).isEqualTo("test image url")
		);
	}

	@DisplayName("VehicleTypeEntity 테스트")
	@Test
	void VehicleTypeEntityTest() {
		// given
		VehicleType vehicleType = VehicleType.builder()
			.vehicleTypesName("test vehicle types name")
			.deletedAt(null)
			.build();
		vehicleTypesJpaRepository.save(VehicleTypeEntity.from(vehicleType));

		// when
		List<VehicleType> vehicleTypes = vehicleTypesJpaRepository.findAll().stream()
			.map(VehicleTypeEntity::toDomain)
			.toList();

		// then
		assertThat(vehicleTypes).hasSize(1);

		VehicleType savedVehicleType = vehicleTypes.getFirst();

		assertAll(
			() -> assertThat(savedVehicleType.getId()).isNotNull(),
			() -> assertThat(savedVehicleType.getCreatedAt()).isNotNull(),
			() -> assertThat(savedVehicleType.getUpdatedAt()).isNotNull(),
			() -> assertThat(savedVehicleType.getVehicleTypesName()).isEqualTo("test vehicle types name")
		);
	}

	@DisplayName("CycleInfoEntity 테스트")
	@Test
	void CycleInfoEntityTest() {
		// given
		CycleInfo cycleInfo = CycleInfo.builder()
			.status(GpsStatus.A)
			.lat(BigDecimal.ONE)
			.lon(BigDecimal.ONE)
			.ang(999)
			.spd(999)
			.intervalAt(LocalDateTime.now())
			.deletedAt(null)
			.build();
		cycleInfoJpaRepository.save(CycleInfoEntity.from(cycleInfo));

		// when
		List<CycleInfo> cycleInfos = cycleInfoJpaRepository.findAll().stream()
			.map(CycleInfoEntity::toDomain)
			.toList();

		// then
		assertThat(cycleInfos).hasSize(1);

		CycleInfo savedCycleInfo = cycleInfos.getFirst();

		assertAll(
			() -> assertThat(savedCycleInfo.getId()).isNotNull(),
			() -> assertThat(savedCycleInfo.getCreatedAt()).isNotNull(),
			() -> assertThat(savedCycleInfo.getUpdatedAt()).isNotNull(),
			() -> assertThat(savedCycleInfo.getStatus()).isEqualTo(GpsStatus.A),
			() -> assertThat(savedCycleInfo.getLat()).isEqualTo(BigDecimal.ONE),
			() -> assertThat(savedCycleInfo.getLon()).isEqualTo(BigDecimal.ONE),
			() -> assertThat(savedCycleInfo.getAng()).isEqualTo(999),
			() -> assertThat(savedCycleInfo.getSpd()).isEqualTo(999)
		);
	}

	@DisplayName("VehicleInformationEntity 테스트")
	@Test
	void VehicleInformationEntityTest() {
		// given
		VehicleInformation vehicleInformation = VehicleInformation.builder()
			.vehicleTypeId(999L)
			.mdn("test mdn")
			.tid("test tid")
			.mid(999)
			.pv(999)
			.did(999)
			.sum(999)
			.deletedAt(null)
			.build();
		vehicleInformationJpaRepository.save(VehicleInformationEntity.from(vehicleInformation));

		// when
		List<VehicleInformation> vehicleInformations = vehicleInformationJpaRepository.findAll().stream()
			.map(VehicleInformationEntity::toDomain)
			.toList();

		// then
		assertThat(vehicleInformations).hasSize(1);

		VehicleInformation savedVehicleInformation = vehicleInformations.getFirst();

		assertAll(
			() -> assertThat(savedVehicleInformation.getId()).isNotNull(),
			() -> assertThat(savedVehicleInformation.getCreatedAt()).isNotNull(),
			() -> assertThat(savedVehicleInformation.getUpdatedAt()).isNotNull(),
			() -> assertThat(savedVehicleInformation.getMdn()).isEqualTo("test mdn"),
			() -> assertThat(savedVehicleInformation.getTid()).isEqualTo("test tid"),
			() -> assertThat(savedVehicleInformation.getMid()).isEqualTo(999),
			() -> assertThat(savedVehicleInformation.getPv()).isEqualTo(999),
			() -> assertThat(savedVehicleInformation.getDid()).isEqualTo(999),
			() -> assertThat(savedVehicleInformation.getSum()).isEqualTo(999)
		);
	}

	@DisplayName("VehicleEventEntity 테스트")
	@Test
	void VehicleEventEntityTest() {
		// given
		VehicleEvent vehicleEvent = VehicleEvent.builder()
			.vehicleId(999L)
			.type(VehicleEventType.ON)
			.eventAt(LocalDateTime.now())
			.deletedAt(null)
			.build();
		vehicleEventJpaRepository.save(VehicleEventEntity.from(vehicleEvent));

		// when
		List<VehicleEvent> vehicleEvents = vehicleEventJpaRepository.findAll().stream()
			.map(VehicleEventEntity::toDomain)
			.toList();

		// then
		assertThat(vehicleEvents).hasSize(1);

		VehicleEvent savedVehicleEvent = vehicleEvents.getFirst();

		assertAll(
			() -> assertThat(savedVehicleEvent.getId()).isNotNull(),
			() -> assertThat(savedVehicleEvent.getCreatedAt()).isNotNull(),
			() -> assertThat(savedVehicleEvent.getUpdatedAt()).isNotNull(),
			() -> assertThat(savedVehicleEvent.getVehicleId()).isEqualTo(999L),
			() -> assertThat(savedVehicleEvent.getType()).isEqualTo(VehicleEventType.ON)
		);
	}

	@DisplayName("DrivingHistoryEntity 테스트")
	@Test
	void DrivingHistoryEntityTest() {
		// given
		DrivingHistory drivingHistory = DrivingHistory.builder()
			.vehicleId(999L)
			.departmentId(999L)
			.driverEmail("test driver email")
			.initialOdometer(100.0)
			.finalOdometer(100.0)
			.drivingDistance(100.0)
			.businessCommuteDistance(100.0)
			.businessUsageDistance(100.0)
			.isBusinessUse(true)
			.usedAt(LocalDateTime.now())
			.onTime(LocalDateTime.now())
			.offTime(LocalDateTime.now())
			.deletedAt(null)
			.build();
		drivingHistoryJpaRepository.save(DrivingHistoryEntity.from(drivingHistory));

		// when
		List<DrivingHistory> drivingHistories = drivingHistoryJpaRepository.findAll().stream()
			.map(DrivingHistoryEntity::toDomain)
			.toList();

		// then
		assertThat(drivingHistories).hasSize(1);

		DrivingHistory savedDrivingHistory = drivingHistories.getFirst();

		assertAll(
			() -> assertThat(savedDrivingHistory.getId()).isNotNull(),
			() -> assertThat(savedDrivingHistory.getCreatedAt()).isNotNull(),
			() -> assertThat(savedDrivingHistory.getUpdatedAt()).isNotNull(),
			() -> assertThat(savedDrivingHistory.getDriverEmail()).isEqualTo("test driver email"),
			() -> assertThat(savedDrivingHistory.getInitialOdometer()).isEqualTo(100.0),
			() -> assertThat(savedDrivingHistory.getFinalOdometer()).isEqualTo(100.0),
			() -> assertThat(savedDrivingHistory.getDrivingDistance()).isEqualTo(100.0),
			() -> assertThat(savedDrivingHistory.getBusinessCommuteDistance()).isEqualTo(100.0),
			() -> assertThat(savedDrivingHistory.getBusinessUsageDistance()).isEqualTo(100.0),
			() -> assertThat(savedDrivingHistory.getIsBusinessUse()).isEqualTo(true)
		);
	}
}
