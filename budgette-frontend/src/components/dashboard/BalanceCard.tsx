interface BalanceCardProps {
  title: string
  balance: number
  currency: string
  operator?: string
  icon?: string
  className?: string
}

export function BalanceCard({ title, balance, currency, operator, icon, className = '' }: BalanceCardProps) {
  const formatted = new Intl.NumberFormat('fr-BJ', {
    minimumFractionDigits: 0,
    maximumFractionDigits: 0,
  }).format(balance)

  return (
    <div className={`bg-white rounded-xl border border-gray-200 p-6 ${className}`}>
      <div className="flex items-center justify-between mb-4">
        <p className="text-sm font-medium text-gray-500">{title}</p>
        {icon && <span className="text-2xl">{icon}</span>}
      </div>
      <p className="text-3xl font-bold text-gray-900">
        {formatted}
        <span className="text-lg font-normal text-gray-500 ml-2">{currency}</span>
      </p>
      {operator && (
        <p className="text-xs text-gray-400 mt-2">{operator}</p>
      )}
    </div>
  )
}
