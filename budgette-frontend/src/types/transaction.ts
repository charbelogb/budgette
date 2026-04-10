export type TransactionType = 'SEND' | 'RECEIVE' | 'WITHDRAW' | 'DEPOSIT' | 'PAYMENT' | 'AIRTIME'

export interface Transaction {
  id: string
  accountId: string
  type: TransactionType
  amount: number
  fees: number
  balanceAfter: number
  counterpartyName: string
  counterpartyPhone: string
  description: string
  date: string
}

export interface DashboardData {
  totalBalance: number
  currency: string
  accounts: import('./account').Account[]
  recentTransactions: Transaction[]
}
