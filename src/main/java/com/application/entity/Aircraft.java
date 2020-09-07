package com.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.application.constants.EnumAircraftSize;
import com.application.constants.EnumAircraftType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "SC_AIRCRAFT")
public class Aircraft {
    
    @Enumerated(EnumType.STRING)
    private EnumAircraftType aircraftType;
    
    @Enumerated(EnumType.STRING)
    private EnumAircraftSize aircraftSize;
    
    @Column(name = "env_name", columnDefinition = "nvarchar(255)")
    private String aircraftName;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "int")
    private Integer aircraftId;
}