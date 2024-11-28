class Calendar {
    constructor() {
        this.events = [];
    }

    addEvent(event) {
        this.events.push(event);
    }

    removeEvent(event) {
        this.events = this.events.filter(e => e != event);
    }

    updateEvent(event) {
        // event
    }
}

class Event {
    constructor(title, description, startDate, endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    getDuration() {
        return (this.endDate - this.startDate) / (1000 * 60);
    }
}

class DateUtil {

}