package com.c8y.ms.templates.metrics.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c8y.ms.templates.metrics.service.MeasurementService;
import com.cumulocity.rest.representation.measurement.MeasurementRepresentation;
import com.google.common.base.Optional;

@RestController
@RequestMapping(path = "/measurements")
public class MeasurementController {

	private final MeasurementService measurementService;

	public MeasurementController(final MeasurementService measurementService) {
		this.measurementService = measurementService;
	}

	@GetMapping(path = "/latest/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getLatestMeasurementForDevice(final @PathVariable String deviceId) {
		final Optional<MeasurementRepresentation> measurementRepresentationOptional = measurementService.getLatestMeasurement(deviceId);

		if (!measurementRepresentationOptional.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(measurementRepresentationOptional.get().toJSON(), HttpStatus.OK);
	}
}
