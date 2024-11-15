export default [
    {
        title: 'Dashboard',
        to: { name: 'customer-Dashboard' },
        icon: { icon: 'bx-home-alt' },
        action: 'read',
        subject: 'Customer'
    },
    {
        title: 'Events',
        to: { name: 'customer-ViewEvents' },
        icon: { icon: 'bx-calendar-event' },
        action: 'read',
        subject: 'Customer'
    },
    {
        title: 'Transactions',
        to: { name: 'customer-ViewTransactions' },
        icon: { icon: 'bx-detail' },
        action: 'read',
        subject: 'Customer'
    },
    {
        title: 'Dashboard',
        to: { name: 'manager-Dashboard' },
        icon: { icon: 'bx-home-alt' },
        action: 'read',
        subject: 'Manager'
    },
    {
        title: 'Create Event',
        to: { name: 'manager-CreateEvent' },
        icon: { icon: 'bx-calendar-plus' },
        action: 'read',
        subject: 'Manager'
    },
    {
        title: 'Events',
        to: { name: 'manager-ViewEvents' },
        icon: { icon: 'bx-calendar-event' },
        action: 'read',
        subject: 'Manager'
    },
    {
        title: 'Dashboard',
        to: { name: 'ticketOfficer-Dashboard' },
        icon: { icon: 'bx-home-alt' },
        action: 'read',
        subject: 'TicketOfficer'
    },
    {
        title: 'Issue Ticket',
        to: { name: 'ticketOfficer-issue-ticket' },
        icon: { icon: 'bx-edit' },
        action: 'read',
        subject: 'TicketOfficer'
    },
    {
        title: 'Verify Tickets',
        to: { name: 'ticketOfficer-VerifyTicket' },
        icon: { icon: 'bx-barcode-reader' },
        action: 'read',
        subject: 'TicketOfficer'
    },


]
