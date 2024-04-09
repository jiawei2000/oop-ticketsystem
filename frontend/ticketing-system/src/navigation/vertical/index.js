export default [
    {
        title: 'Dashboard',
        to: { name: 'index' },
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
        title: 'Create Event',
        to: { name: 'manager-CreateEvent' },
        icon: { icon: 'bx-calendar-plus' },
        action: 'read',
        subject: 'Customer'
    },
]
