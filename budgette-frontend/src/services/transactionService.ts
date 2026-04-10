import api from './api'
import type { Transaction, DashboardData } from '@/types/transaction'

export const transactionService = {
  async getAllTransactions(): Promise<Transaction[]> {
    const response = await api.get<Transaction[]>('/transactions')
    return response.data
  },

  async getTransactionsByAccount(accountId: string): Promise<Transaction[]> {
    const response = await api.get<Transaction[]>(`/transactions/account/${accountId}`)
    return response.data
  },

  async syncTransactions(accountId: string): Promise<Transaction[]> {
    const response = await api.post<Transaction[]>(`/transactions/account/${accountId}/sync`)
    return response.data
  },

  async getDashboard(): Promise<DashboardData> {
    const response = await api.get<DashboardData>('/dashboard')
    return response.data
  },
}
