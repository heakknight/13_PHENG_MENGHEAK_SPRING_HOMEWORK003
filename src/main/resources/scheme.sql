CREATE DATABASE spring_homework03;

CREATE TABLE venues (
    venue_id SERIAL PRIMARY KEY,
    venue_name VARCHAR(255) NOT NULL,
    location VARCHAR(255)
);

CREATE TABLE events (
    event_id SERIAL PRIMARY KEY,
    event_name VARCHAR(255) NOT NULL,
    event_date TIMESTAMP,
    venue_id INT,
    CONSTRAINT fk_venue FOREIGN KEY(venue_id) REFERENCES venues(venue_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE attendees (
   attendee_id SERIAL PRIMARY KEY,
   attendee_name VARCHAR(255) NOT NULL,
   email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE event_attendee (
    attendee_id INT,
    event_id INT,
    PRIMARY KEY (attendee_id, event_id),
    CONSTRAINT fk_attendee FOREIGN KEY(attendee_id) REFERENCES attendees(attendee_id) ON DELETE CASCADE,
    CONSTRAINT fk_event FOREIGN KEY(event_id)REFERENCES events(event_id) ON DELETE CASCADE
);
